package br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Cliente;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ClienteDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
   @Mapping(source = "id", target = "idCliente")
   ClienteDTO toDto(Cliente cliente);
  
  @Mapping(source = "idCliente", target = "id")
   Cliente toEntity(ClienteDTO clienteDTO);

   List<Cliente> toEntityList(List<ClienteDTO> clienteDTOList);
   List<ClienteDTO> toDtoList(List<Cliente> clienteList);
}
