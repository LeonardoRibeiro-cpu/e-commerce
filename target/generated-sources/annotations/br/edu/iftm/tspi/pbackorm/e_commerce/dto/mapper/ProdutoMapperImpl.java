package br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Categoria;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Produto;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-04T15:43:10-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public Produto toEntity(ProdutoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setCategoria( produtoDTOToCategoria( dto ) );
        produto.setCaminhoImagem( dto.getCaminhoImagem() );
        produto.setEstoque( dto.getEstoque() );
        produto.setId( dto.getId() );
        produto.setNome( dto.getNome() );
        produto.setPreco( dto.getPreco() );

        return produto;
    }

    @Override
    public ProdutoDTO toDto(Produto entity) {
        if ( entity == null ) {
            return null;
        }

        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setIdCategoria( entityCategoriaId( entity ) );
        produtoDTO.setCaminhoImagem( entity.getCaminhoImagem() );
        produtoDTO.setEstoque( entity.getEstoque() );
        produtoDTO.setId( entity.getId() );
        produtoDTO.setNome( entity.getNome() );
        produtoDTO.setPreco( entity.getPreco() );

        return produtoDTO;
    }

    @Override
    public List<ProdutoDTO> toDtoList(List<Produto> produtos) {
        if ( produtos == null ) {
            return null;
        }

        List<ProdutoDTO> list = new ArrayList<ProdutoDTO>( produtos.size() );
        for ( Produto produto : produtos ) {
            list.add( toDto( produto ) );
        }

        return list;
    }

    @Override
    public List<Produto> toEntityList(List<ProdutoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Produto> list = new ArrayList<Produto>( dtos.size() );
        for ( ProdutoDTO produtoDTO : dtos ) {
            list.add( toEntity( produtoDTO ) );
        }

        return list;
    }

    protected Categoria produtoDTOToCategoria(ProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setId( produtoDTO.getIdCategoria() );

        return categoria;
    }

    private Integer entityCategoriaId(Produto produto) {
        Categoria categoria = produto.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        return categoria.getId();
    }
}
