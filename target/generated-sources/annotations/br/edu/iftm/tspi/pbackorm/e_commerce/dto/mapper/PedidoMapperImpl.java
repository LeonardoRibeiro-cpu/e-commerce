package br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Cliente;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.DetalhesPedido;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Pedido;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Produto;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.DetalhePedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.PedidoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-21T13:43:58-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public Pedido toEntity(PedidoDTO pedidoDto) {
        if ( pedidoDto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setCliente( pedidoDTOToCliente( pedidoDto ) );
        pedido.setDataPedido( pedidoDto.getDataPedido() );
        pedido.setDetalhesPedido( toDetalhesEntityList( pedidoDto.getDetalhesPedido() ) );
        pedido.setId( pedidoDto.getId() );

        return pedido;
    }

    @Override
    public PedidoDTO toDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();

        pedidoDTO.setIdCliente( pedidoClienteId( pedido ) );
        pedidoDTO.setDataPedido( pedido.getDataPedido() );
        pedidoDTO.setDetalhesPedido( toDetalhesDtoList( pedido.getDetalhesPedido() ) );
        pedidoDTO.setId( pedido.getId() );

        return pedidoDTO;
    }

    @Override
    public DetalhePedidoDTO toDetalheDto(DetalhesPedido detalhe) {
        if ( detalhe == null ) {
            return null;
        }

        DetalhePedidoDTO detalhePedidoDTO = new DetalhePedidoDTO();

        detalhePedidoDTO.setIdPedido( detalhePedidoId( detalhe ) );
        detalhePedidoDTO.setIdProduto( detalheProdutoId( detalhe ) );
        detalhePedidoDTO.setDesconto( detalhe.getDesconto() );
        detalhePedidoDTO.setPrecoVenda( detalhe.getPrecoVenda() );
        detalhePedidoDTO.setQuantidade( detalhe.getQuantidade() );

        return detalhePedidoDTO;
    }

    @Override
    public DetalhesPedido toDetalheEntity(DetalhePedidoDTO detalheDto) {
        if ( detalheDto == null ) {
            return null;
        }

        DetalhesPedido detalhesPedido = new DetalhesPedido();

        detalhesPedido.setPedido( detalhePedidoDTOToPedido( detalheDto ) );
        detalhesPedido.setProduto( detalhePedidoDTOToProduto( detalheDto ) );
        detalhesPedido.setDesconto( detalheDto.getDesconto() );
        detalhesPedido.setPrecoVenda( detalheDto.getPrecoVenda() );
        detalhesPedido.setQuantidade( detalheDto.getQuantidade() );

        return detalhesPedido;
    }

    @Override
    public List<DetalhePedidoDTO> toDetalhesDtoList(List<DetalhesPedido> detalhes) {
        if ( detalhes == null ) {
            return null;
        }

        List<DetalhePedidoDTO> list = new ArrayList<DetalhePedidoDTO>( detalhes.size() );
        for ( DetalhesPedido detalhesPedido : detalhes ) {
            list.add( toDetalheDto( detalhesPedido ) );
        }

        return list;
    }

    @Override
    public List<DetalhesPedido> toDetalhesEntityList(List<DetalhePedidoDTO> detalhesDto) {
        if ( detalhesDto == null ) {
            return null;
        }

        List<DetalhesPedido> list = new ArrayList<DetalhesPedido>( detalhesDto.size() );
        for ( DetalhePedidoDTO detalhePedidoDTO : detalhesDto ) {
            list.add( toDetalheEntity( detalhePedidoDTO ) );
        }

        return list;
    }

    @Override
    public List<Pedido> toEntityList(List<PedidoDTO> PedidoDto) {
        if ( PedidoDto == null ) {
            return null;
        }

        List<Pedido> list = new ArrayList<Pedido>( PedidoDto.size() );
        for ( PedidoDTO pedidoDTO : PedidoDto ) {
            list.add( toEntity( pedidoDTO ) );
        }

        return list;
    }

    @Override
    public List<PedidoDTO> toDtoList(List<Pedido> Pedido) {
        if ( Pedido == null ) {
            return null;
        }

        List<PedidoDTO> list = new ArrayList<PedidoDTO>( Pedido.size() );
        for ( Pedido pedido : Pedido ) {
            list.add( toDto( pedido ) );
        }

        return list;
    }

    protected Cliente pedidoDTOToCliente(PedidoDTO pedidoDTO) {
        if ( pedidoDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( pedidoDTO.getIdCliente() );

        return cliente;
    }

    private String pedidoClienteId(Pedido pedido) {
        Cliente cliente = pedido.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getId();
    }

    private Integer detalhePedidoId(DetalhesPedido detalhesPedido) {
        Pedido pedido = detalhesPedido.getPedido();
        if ( pedido == null ) {
            return null;
        }
        return pedido.getId();
    }

    private Integer detalheProdutoId(DetalhesPedido detalhesPedido) {
        Produto produto = detalhesPedido.getProduto();
        if ( produto == null ) {
            return null;
        }
        return produto.getId();
    }

    protected Pedido detalhePedidoDTOToPedido(DetalhePedidoDTO detalhePedidoDTO) {
        if ( detalhePedidoDTO == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setId( detalhePedidoDTO.getIdPedido() );

        return pedido;
    }

    protected Produto detalhePedidoDTOToProduto(DetalhePedidoDTO detalhePedidoDTO) {
        if ( detalhePedidoDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setId( detalhePedidoDTO.getIdProduto() );

        return produto;
    }
}
