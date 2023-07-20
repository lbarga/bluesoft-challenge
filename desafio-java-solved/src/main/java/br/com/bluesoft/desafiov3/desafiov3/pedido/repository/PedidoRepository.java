package br.com.bluesoft.desafiov3.desafiov3.pedido.repository;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidosPaginadoView;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PedidoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Pedido salvarPedido(Pedido pedido) {
        entityManager.persist(pedido);
        return pedido;
    }

    public List<Pedido> listarTodos() {
        String sql = "FROM Pedido p";
        final Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    public PedidosPaginadoView listarTodosPaginado(int pageNumber, int pageSize) {
        String sql = "FROM Pedido p";

        final Query query = entityManager.createQuery(sql);
        int offset = (pageNumber - 1) * pageSize;
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Pedido> pedidos = query.getResultList();

        String countSql = "SELECT COUNT(p) FROM Pedido p";
        final Query countQuery = entityManager.createQuery(countSql);
        Long totalCount = (Long) countQuery.getSingleResult();

        PedidosPaginadoView pedidosPaginadoView = new PedidosPaginadoView(pedidos, totalCount);

        return pedidosPaginadoView;
    }

    public Pedido buscarPedido(Long pedidoId) {
        String sql = " from Pedido p where p.id = :pedidoId ";

        final Query query = entityManager.createQuery(sql);

        query.setParameter("pedidoId", pedidoId);
        try {
            return (Pedido) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void deletarPedido(Pedido pedido) {
        entityManager.remove(pedido);
    }

}
