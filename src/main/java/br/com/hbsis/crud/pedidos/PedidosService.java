package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import br.com.hbsis.crud.funcionario.Funcionario;
import br.com.hbsis.crud.funcionario.FuncionarioService;
import br.com.hbsis.crud.funcionario.HbemployeesParam;
import br.com.hbsis.crud.periodoAtual.PeriodoAtual;
import br.com.hbsis.crud.periodoAtual.PeriodoAtualServices;
import br.com.hbsis.crud.produto.Produto;
import br.com.hbsis.crud.produto.ProdutoService;
import br.com.hbsis.crud.produtoPedido.ProdutoPedido;
import br.com.hbsis.crud.produtoPedido.ProdutoPedidoServices;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PedidosService {
    private final PedidosRepository pedidosRepository;
    private final FuncionarioService funcionarioService;
    private final ProdutoPedidoServices produtoPedidoServices;
    private final PeriodoAtualServices periodoAtualServices;
    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    public PedidosService(PedidosRepository pedidosRepository, FuncionarioService funcionarioService, ProdutoPedidoServices produtoPedidoServices,
                          PeriodoAtualServices periodoAtualServices, ProdutoService produtoService, FornecedorService fornecedorService) {
        this.pedidosRepository = pedidosRepository;
        this.funcionarioService = funcionarioService;
        this.produtoPedidoServices = produtoPedidoServices;
        this.periodoAtualServices = periodoAtualServices;
        this.produtoService = produtoService;
        this.fornecedorService = fornecedorService;
    }

    //save
    @Transactional
    public Pedido save(PedidosDTO pedidosDTO) {
        //pegar data atual
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(System.currentTimeMillis());


        //salvar pedido


        PeriodoAtual periodoAtual = null;

        Fornecedor fornecedor = this.fornecedorService.findEntityById(pedidosDTO.getFornecedor());

        List<PeriodoAtual> periodoAtualList = this.periodoAtualServices.findAllByFornecedor(fornecedor.getIdFornecedor());

        for (int j = 0; j < periodoAtualList.size(); j++) {
            Date dataIncio = periodoAtualList.get(j).getDataInicio();
            Date dataFinal = periodoAtualList.get(j).getDataFinal();

            if (dataIncio.compareTo(now) < 0 && dataFinal.compareTo(now) > 0) {
                periodoAtual = periodoAtualList.get(j);
                break;
            }
        }

        if (periodoAtual == null) {
            throw new IllegalArgumentException("Nenhum dos produtos escolhidos possuem periodo aberto.");
        }

        Pedido pedido = new Pedido();
        Funcionario entidadeFuncionario = this.funcionarioService.findEntityById(pedidosDTO.getIdFuncionario());
        pedido.setFkFuncionario(entidadeFuncionario);
        pedido.setStatus(0);
        pedido.setDataCriacao(now);
        pedido.setFkPeriodoAtual(periodoAtual);
        pedido = this.pedidosRepository.save(pedido);

        Double valorTotalPedido = 0.0;

        List<InvoiceItemDTOSet> invoiceItemDTOSetList = new ArrayList<>();

        //cadastrar relação produto pedido
        for (int i = 0; i < pedidosDTO.getProdutos().size(); i++) {
            Produto produto = this.produtoService.findEntityById(pedidosDTO.getProdutos().get(i));
            if (produto.getLinha().getCategoria().getFornecedor().getIdFornecedor() == pedidosDTO.getFornecedor()) {
                this.produtoPedidoServices.save(pedido, produto, pedidosDTO.getQtdComprada().get(i));
                valorTotalPedido += produto.getPrecoProduto()*pedidosDTO.getQtdComprada().get(i);
                fornecedor = produto.getLinha().getCategoria().getFornecedor();

                //setar invoice itemDTO set
                InvoiceItemDTOSet invoiceItemDTOSet = new InvoiceItemDTOSet(
                        pedidosDTO.getQtdComprada().get(i),
                        produto.getNomeProduto()
                );

                invoiceItemDTOSetList.add(invoiceItemDTOSet);

            }
        }

        //registrar pedido na api hbemployee


        InvoiceDTO invoiceDTO = new InvoiceDTO(
                fornecedor.getCnpj(),
                entidadeFuncionario.getUuid(),
                valorTotalPedido,
                invoiceItemDTOSetList
        );

        String codigoRetornoApi = this.invoice(invoiceDTO);

        System.out.println(codigoRetornoApi);


        //enviar email
        String mailTo = entidadeFuncionario.getEmailFuncionario();

        this.sendMail(mailTo, periodoAtual.getDataRetirada().toString());

        return pedido;
    }

    //listar
    public Pedido findById(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        if (pedidosOptional.isPresent()) {
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("O pedido fornecido(id %s) não existe", id));
    }

    //listar tudo

    public List<Pedido> findAll() {
        List<Pedido> pedidoList = this.pedidosRepository.findAll();

        if (pedidoList.isEmpty()) {
            throw new NoResultException("Não foi encontrado nenhum pedido.");
        }

        return pedidoList;
    }

    //pegar entidade
    public Pedido findEntityById(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        if (pedidosOptional.isPresent()) {
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("O pedido informado(id %s) não existe", id));
    }

    //deletar
    public String delete(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        if (pedidosOptional.get().getStatus() != 1) {
            List<ProdutoPedido> produtoPedido = this.produtoPedidoServices.findProdutoPedidoByPedido(pedidosOptional.get());

            if (pedidosOptional.isPresent()) {
                produtoPedido.forEach(
                        produtoPedido1 -> {
                            this.produtoPedidoServices.delete(produtoPedido1);
                        }
                );
                this.pedidosRepository.deleteById(id);
                return String.format("Pedido de id %s deletado com sucesso", id);
            }
            throw new IllegalArgumentException(String.format("O pedido informado(id %s) não existe", id));
        }
        return String.format("O pedido informado(%s) já foi retirado, portanto não pode ser cancelado", id);
    }

    //listar por funcionario

    public List<List<ProdutoPedido>> listByFuncionario(int id) {
        List<Integer> idLista = this.pedidosRepository.findPedidoByIdFuncionario(this.funcionarioService.findEntityById(id));

        List<Pedido> pedidos = new ArrayList<>();

        idLista.forEach(integer -> pedidos.add(this.pedidosRepository.findById(integer).get()));

        return this.produtoPedidoServices.listByFuncionario(pedidos);
    }

    //atualizar informações
    public Pedido update(PedidosDTO pedidosDTO, int idPedido) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(idPedido);

        if (pedidosOptional.isPresent()) {
            Pedido pedidoExistente = pedidosOptional.get();
            if (pedidoExistente.getStatus() != 1) {
                pedidoExistente.setFkFuncionario(this.funcionarioService.findEntityById(pedidosDTO.getIdFuncionario()));
                pedidoExistente.setStatus(0);
                pedidoExistente = this.pedidosRepository.save(pedidoExistente);

                return pedidoExistente;
            }
            throw new IllegalArgumentException("O pedido já foi retirado ou cancelado, portanto não pode mais ser alterado.");
        }
        throw new IllegalArgumentException(String.format("ID(%s) do pedido não existe", idPedido));
    }

    //retirar
    public String retirarPedido(int idPedido) {
        Optional<Pedido> pedidoOptional = this.pedidosRepository.findById(idPedido);

        if (pedidoOptional.isPresent()) {
            Pedido pedidoExistente = pedidoOptional.get();

            pedidoExistente.setStatus(1);

            pedidoExistente = this.pedidosRepository.save(pedidoExistente);
            return "Pedido retirado com sucesso.";
        }
        throw new IllegalArgumentException(String.format("O pedido(%s) não existe.", idPedido));
    }

    //cancelar pedido

    public String cancelarPedido(int idPedido) {
        Optional<Pedido> pedidoOptional = this.pedidosRepository.findById(idPedido);

        if (pedidoOptional.isPresent()) {
            if (pedidoOptional.get().getStatus() != 1) {
                Pedido pedidoExistente = pedidoOptional.get();
                pedidoExistente.setStatus(2);
                return "Pedido cancelado com sucesso.";
            }
            throw new IllegalArgumentException(String.format("O pedido informado já foi retirado ou cancelado, portanto não pode ser cancelado."));
        }
        throw new IllegalArgumentException(String.format("O pedido informado(%s) não existe.", idPedido));
    }

    //enviar email
    public void sendMail(String emailTo, String date) {
        new Thread(() -> {
            Properties properties = new Properties();

            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("hbsis.test", "hbsistest2019@");
                }
            });

            try {
                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress("hbsis.test@gmail.com"));

                Address[] toUser = InternetAddress //Destinatário(s)
                        .parse(emailTo);

                message.setRecipients(Message.RecipientType.TO, toUser);
                message.setSubject("Data de retirada do seu pedido.");//Assunto
                message.setText("Data de retirada: " + date);


                Transport.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //achar pedido por funcionario por fornecedor
    public List<Pedido> findByFuncionario(Funcionario funcionario) {
        return this.pedidosRepository.findByFkFuncionario(funcionario);
    }

    //achar pedidos por periodo de vendas
    public List<Pedido> findPedidosByPeriodo(PeriodoAtual periodoAtual){
        List<Pedido> pedidoList = this.pedidosRepository.findByFkPeriodoAtual(periodoAtual);

        if(!pedidoList.isEmpty()){
            return pedidoList;
        }
        throw new NoResultException(String.format("Não existe nenhum pedido para o periodo informado(%s)", periodoAtual.getId()));
    }

    //registrar hbemployee invoice
    public String invoice(InvoiceDTO invoiceDTO){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f59fe1c4-1b67-11ea-978f-2e728ce88125");

        HttpEntity<InvoiceDTO> entity = new HttpEntity<InvoiceDTO>(invoiceDTO, headers);

        ResponseEntity<InvoiceDTO> result = restTemplate.exchange("http://nt-04053:9999/api/invoice", HttpMethod.POST, entity, InvoiceDTO.class);

        return result.getStatusCode().toString();
    }
}
