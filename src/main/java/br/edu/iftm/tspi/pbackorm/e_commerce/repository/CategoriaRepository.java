package br.edu.iftm.tspi.pbackorm.e_commerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ValorTotalPorCategoriaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Categoria;
@Repository
public  interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    @Query("""
        SELECT c.nome AS nomeCategoria, SUM((dp.precoVenda - dp.desconto) * dp.quantidade) AS valorTotal
        FROM DetalhesPedido dp
        JOIN dp.produto p
        JOIN p.categoria c
        WHERE c.id = :id
    """)
    List<ValorTotalPorCategoriaDTO> buscaTotalPorCategoria(Integer id);
}
