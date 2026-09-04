package br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Cliente;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ClienteDTO;
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
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setIdCliente( cliente.getId() );
        clienteDTO.setCep( cliente.getCep() );
        clienteDTO.setCidade( cliente.getCidade() );
        clienteDTO.setEndereco( cliente.getEndereco() );
        clienteDTO.setFax( cliente.getFax() );
        clienteDTO.setNome( cliente.getNome() );
        clienteDTO.setPais( cliente.getPais() );
        clienteDTO.setTelefone( cliente.getTelefone() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( clienteDTO.getIdCliente() );
        cliente.setCep( clienteDTO.getCep() );
        cliente.setCidade( clienteDTO.getCidade() );
        cliente.setEndereco( clienteDTO.getEndereco() );
        cliente.setFax( clienteDTO.getFax() );
        cliente.setNome( clienteDTO.getNome() );
        cliente.setPais( clienteDTO.getPais() );
        cliente.setTelefone( clienteDTO.getTelefone() );

        return cliente;
    }

    @Override
    public List<Cliente> toEntityList(List<ClienteDTO> clienteDTOList) {
        if ( clienteDTOList == null ) {
            return null;
        }

        List<Cliente> list = new ArrayList<Cliente>( clienteDTOList.size() );
        for ( ClienteDTO clienteDTO : clienteDTOList ) {
            list.add( toEntity( clienteDTO ) );
        }

        return list;
    }

    @Override
    public List<ClienteDTO> toDtoList(List<Cliente> clienteList) {
        if ( clienteList == null ) {
            return null;
        }

        List<ClienteDTO> list = new ArrayList<ClienteDTO>( clienteList.size() );
        for ( Cliente cliente : clienteList ) {
            list.add( toDto( cliente ) );
        }

        return list;
    }
}
