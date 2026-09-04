package br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Categoria;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.CategoriaDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-04T15:43:12-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class CategoriaMapperImpl implements CategoriaMapper {

    @Override
    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        if ( categoriaDTO == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setDescricao( categoriaDTO.getDescricao() );
        categoria.setId( categoriaDTO.getId() );
        categoria.setNome( categoriaDTO.getNome() );

        return categoria;
    }

    @Override
    public CategoriaDTO toDto(Categoria categoria) {
        if ( categoria == null ) {
            return null;
        }

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        categoriaDTO.setDescricao( categoria.getDescricao() );
        categoriaDTO.setId( categoria.getId() );
        categoriaDTO.setNome( categoria.getNome() );

        return categoriaDTO;
    }

    @Override
    public List<Categoria> toEntityList(List<CategoriaDTO> CategoriaDto) {
        if ( CategoriaDto == null ) {
            return null;
        }

        List<Categoria> list = new ArrayList<Categoria>( CategoriaDto.size() );
        for ( CategoriaDTO categoriaDTO : CategoriaDto ) {
            list.add( toEntity( categoriaDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoriaDTO> toDtoList(List<Categoria> Categoria) {
        if ( Categoria == null ) {
            return null;
        }

        List<CategoriaDTO> list = new ArrayList<CategoriaDTO>( Categoria.size() );
        for ( Categoria categoria : Categoria ) {
            list.add( toDto( categoria ) );
        }

        return list;
    }
}
