package br.com.bluesoft.desafiov3.desafiov3.pedido.web.controller;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.ItemPedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.ItemPedidoView;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidoView;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidosPaginadoView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void criarUmNovoPedido() throws Exception {
        URI uri = new URI("/pedidos");

        // cria o formulário que será enviado via json
        PedidoFormulario form = criarFormularioComUmItem(false);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(form);

        // faz a requisição e já garante 200 como resposta.
        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_CREATED));

        // transforma a resposta em objeto para verificar se o mesmo foi criado corretamente
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        PedidoView pedidoCriado = objectMapper.readValue(contentAsString, PedidoView.class);

        assertEquals(form.getFormaPagamento(), pedidoCriado.getFormaPagamento());
        assertTrue(pedidoCriado.isRetiradaNaLoja());

        final List<ItemPedidoView> itensCriados = pedidoCriado.getItens();
        assertEquals(1, itensCriados.size());

        final ItemPedidoView itemPedido = itensCriados.get(0);
        assertEquals(10, itemPedido.getQuantidade());

        // garantindo que o item pertence ao pedido
        assertEquals(pedidoCriado.getId(), itemPedido.getPedidoId());
    }


    @Test
    public void listarTodosOsPedidos() throws Exception {
        URI uri = new URI("/pedidos");

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        PedidosPaginadoView pedidosPaginadoView = objectMapper.readValue(contentAsString, PedidosPaginadoView.class);

        assertEquals(6, pedidosPaginadoView.getTotalPedidos());
        assertEquals(6, pedidosPaginadoView.getPedidos().size());
    }

    @Test
    public void deletarPedido() throws Exception {
        // vamos deletar o pedido de id 1
        URI uri = new URI("/pedidos/1");

        // faz a requisição e já garante 200 como resposta.
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        // agora iremos buscar o pedido pelo id e não tem que vir nada.
        URI uriBuscaPedido = new URI("/pedidos/1");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uriBuscaPedido))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_NOT_FOUND));
    }

    @Test
    public void listarQuantidadeDePedidosPorFormaDePagamento() throws Exception {
        URI uri = new URI("/pedidos/forma-pagamentos");

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<FormaPagamento, Long> map = objectMapper.readValue(contentAsString, new TypeReference<Map<FormaPagamento, Long>>() {
        });

        assertEquals(3, map.size());
        assertEquals(2L, map.get(FormaPagamento.CREDITO));
        assertEquals(2L, map.get(FormaPagamento.DEBITO));
        assertEquals(2L, map.get(FormaPagamento.CHEQUE));

    }

    @Test
    public void lancarExceptionCasoTenhaMaisDe50Itens() throws Exception {
        URI uri = new URI("/pedidos");

        // cria o formulário com mais de 50 itens
        PedidoFormulario form = criarFormularioComMaisDe50Itens();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(form);

        // faz a requisição e garante que sera lançada uma exception
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                            .post(uri)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        });

        // recebe a messagem da exception
        String actualMessage = exception.getMessage();

        // verifica se contem a mensagem customizada
        boolean messageOk = actualMessage.indexOf("O maximo de itens por pedido e 50!") > -1;

        assertTrue(messageOk);
    }

    @Test
    public void lancarExceptionCasoTenhaFlagSimularFalha() throws Exception {
        URI uri = new URI("/pedidos");

        // cria o formulário com mais de 50 itens
        PedidoFormulario form = criarFormularioComUmItem(true);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(form);

        // faz a requisição e garante que sera lançada uma exception
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                            .post(uri)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        });

        // recebe a messagem da exception
        String actualMessage = exception.getMessage();

        // verifica se contem a mensagem customizada
        boolean messageOk = actualMessage.indexOf("Estoque está vazio!") > -1;

        assertTrue(messageOk);
    }

    private PedidoFormulario criarFormularioComUmItem(Boolean simularFalha) {
        ItemPedidoFormulario item = new ItemPedidoFormulario("Coca-Cola", 10);

        List<ItemPedidoFormulario> itemFormulario = new ArrayList<>();

        itemFormulario.add(item);

        PedidoFormulario form = new PedidoFormulario(true, simularFalha, FormaPagamento.CREDITO, itemFormulario);

        return form;
    }


    private PedidoFormulario criarFormularioComMaisDe50Itens() {
        ItemPedidoFormulario item = new ItemPedidoFormulario("Coca-Cola", 60);

        List<ItemPedidoFormulario> itemFormulario = new ArrayList<>();

        itemFormulario.add(item);

        PedidoFormulario form = new PedidoFormulario(true, false, FormaPagamento.CREDITO, itemFormulario);

        return form;
    }

}