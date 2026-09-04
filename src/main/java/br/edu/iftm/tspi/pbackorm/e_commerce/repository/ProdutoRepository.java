package br.edu.iftm.tspi.pbackorm.e_commerce.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Produto;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutosPorPedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.UnidadesCompradasDTO;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

     List<Produto> findByNomeContainingIgnoreCaseAndEstoqueGreaterThanEqualAndPrecoLessThanEqual(
        String nome, Short estoqueMin, Double precoMax
     );
         public List<Produto> 
        findByNomeContainingIgnoreCaseAndEstoqueGreaterThanAndPrecoLessThan(
            String nome,Short estoqueMin, Double precoMax);

    @Query("""
              SELECT new br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO(
                    p.id,p.nome,p.preco,p.estoque,p.caminhoImagem,p.categoria.id)
              FROM Produto p
              WHERE p.preco = (
                    SELECT MAX(p2.preco)
                    FROM Produto p2
                    WHERE p2.categoria.id = p.categoria.id
                    )      
            """)
    public List<ProdutoDTO> buscarProdutosComMaiorPrecoPorCategoria();


    @Query(nativeQuery = true, value = """
            Select p.produtoID,p.produtoNome,p.preco,
                   p.unidadesemestoque,p.imagem,p.categoriaID
            FROM Produtos p
            where p.preco = (
                    Select MAX(p2.preco)
                    From Produtos p2
                    Where p2.categoriaID = p.categoriaID
                      AND p.categoriaID = :categoriaID
                    )
            """)
    public List<ProdutoDTO> buscarProdutosComMaiorPrecoPorCategoriaNativo(
        @Param("categoriaID") Integer categoriaID);

    @Query(nativeQuery=true,value="""
                    Select produtoNome as nomeProduto,
                           sum(dp.quantidade) as unidadesCompradas
                    From detalhes_pedido dp inner join produtos p on dp.produtoid = p.produtoid
                                            inner join pedidos pd on dp.pedidoid = pd.pedidoid
                    Where datapedido between :dataInicio and :dataFim
                    Group by produtoNome
                    Order by unidadesCompradas desc
                    """)
    public List<UnidadesCompradasDTO> findUnidadesCompradasPeriodo(
         @Param("dataInicio") LocalDate inicio,
         @Param("dataFim") LocalDate fim);    


        @Query("""
                Select   p.nome as nomeProduto, sum(dp.quantidade) as unidadesCompradas
                From DetalhesPedido dp  join dp.produto p
                                       join dp.pedido pd
                Where pd.dataPedido between :dataInicio and :dataFim
                Group by p.nome
                Order by sum(dp.quantidade) desc
                """)
    public List<UnidadesCompradasDTO> findUnidadesCompradasPeriodoJPQL(
        @Param("dataInicio") LocalDateTime inicio,
        @Param("dataFim") LocalDateTime fim);



        @Query("""
                Select   p.nome as nomeProduto, sum(dp.quantidade) as unidadesCompradas
                From DetalhesPedido dp  join dp.produto p
                                       join dp.pedido pd
               where pd.dataPedido between :dataInicio and :dataFim
                 and (:produtoId is null or p.id = :produtoId)
                Group by p.nome
                Order by sum(dp.quantidade) desc
                """)
        public List<UnidadesCompradasDTO> findbyBuscarUnidadeCompradaPorIdParametroIdNaoObrigatorio(
            @Param("dataInicio") LocalDateTime inicio,
            @Param("dataFim") LocalDateTime fim,
            @Param("produtoId") Integer produtoId
        );


        @Query("""
                Select   pd.id as numeroPedido, sum((dp.precoVenda-dp.desconto)*dp.quantidade) as valorTotal, sum(dp.quantidade) as quantidade, sum(dp.desconto) as desconto
                From DetalhesPedido dp  join dp.produto p
                                       join dp.pedido pd
                 where p.id = :produtoId
                Group by pd.id
                """)
        public List<ProdutosPorPedidoDTO> findbyRetornarPedidosPorIdProduto(
            @Param("produtoId") Integer produtoId
        );
}
