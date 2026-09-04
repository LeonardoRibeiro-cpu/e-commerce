package br.edu.iftm.tspi.pbackorm.e_commerce.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.PedidosProdutosDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Cliente;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoPorIdClienteDTO;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findByCidade(String cidade);
    List<Cliente> findByPais(String pais);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    boolean existsById(String id);

    @Query("""
        SELECT p.nome AS produtoNome, SUM((dp.precoVenda - dp.desconto)* dp.quantidade) AS valorTotal
        FROM DetalhesPedido dp
        JOIN dp.produto p
        JOIN dp.pedido pe
        JOIN pe.cliente c
        WHERE c.id = :id
        GROUP BY p.nome
            """)
    List<ProdutoPorIdClienteDTO> findProdutosById(String id);

    @Query("""
    SELECT 
        pd.id AS numeroPedido, p.nome AS nomeProduto,
        SUM(dp.quantidade) AS quantidade,
        SUM((dp.precoVenda - dp.desconto) * dp.quantidade) AS valorTotal
        FROM DetalhesPedido dp
        JOIN dp.produto p
        JOIN dp.pedido pd
        JOIN pd.cliente c
        WHERE c.id = :clienteId
         AND pd.dataPedido BETWEEN :dataInicio AND :dataFim
        GROUP BY pd.id, p.nome
        ORDER BY pd.id
                           """)
    List<PedidosProdutosDTO> buscarPedidosComProdutos(
     @Param("clienteId") String clienteId,
     @Param("dataInicio") LocalDateTime dataInicio,
     @Param("dataFim") LocalDateTime dataFim
);

}
