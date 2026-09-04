package br.edu.iftm.tspi.pbackorm.e_commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.DetalhesPedido;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.DetalhePedidoID;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Pedido;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.DetalhePedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.PedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.PedidoMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.service.PedidoService;
import br.edu.iftm.tspi.pbackorm.e_commerce.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {
     private final PedidoRepository repository;
       private final PedidoService pedidoService;
    private final PedidoMapper mapper;

    @GetMapping
    public List<PedidoDTO> listar(@RequestParam(required = false) String clienteNome,
                                 @RequestParam(required = false) Integer pedidoId,
                                 @RequestParam(required = false) String clienteId) {
          List<Pedido> pedidos;
         if (!(clienteNome == null)) {
            pedidos = repository.findByClienteNomeContainingIgnoreCase(clienteNome);
        } else if (pedidoId != null) {
            pedidos = repository.findById(pedidoId)
                    .map(List::of)
                    .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + pedidoId + " não encontrado"));
        }else if (clienteId != null) {
            pedidos = repository.findByClienteId(clienteId);
        } else {
            pedidos = repository.findAll();
        }
        return mapper.toDtoList(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Integer id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + id + " não encontrado"));
        PedidoDTO pedidoDTO = mapper.toDto(pedido);
        return ResponseEntity.ok().body(pedidoDTO);
    }

     @PostMapping
    public ResponseEntity<PedidoDTO> salvar(@Valid @RequestBody PedidoDTO pedidoNovoDTO) {
        Pedido pedidoNovo = mapper.toEntity(pedidoNovoDTO);        
        Pedido pedidoSalvo = pedidoService.salvar(pedidoNovo);
        PedidoDTO pedidoSalvoDto = mapper.toDto(pedidoSalvo);
        return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(pedidoSalvoDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pedido de id " + id + " não encontrado");
        }
        Pedido pedido = mapper.toEntity(pedidoDTO);
        pedido.setId(id);
        Pedido pedidoAtualizado = repository.save(pedido);
        PedidoDTO pedidoAtualizadoDTO = mapper.toDto(pedidoAtualizado);
        return ResponseEntity.ok().body(pedidoAtualizadoDTO);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pedido de id " + id + " não encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{pedidoID}/itens")
    public ResponseEntity<List<DetalhePedidoDTO>> listarItens(@PathVariable Integer pedidoID) {
        Pedido pedido = repository.findById(pedidoID)
                .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + pedidoID + " não encontrado"));
        List<DetalhesPedido> detalhes = pedido.getDetalhesPedido();
        List<DetalhePedidoDTO> detalhesDTO = mapper.toDetalhesDtoList(detalhes);
        return ResponseEntity.ok().body(detalhesDTO);
    }

    @PostMapping("/{pedidoID}/itens")
    public ResponseEntity<DetalhePedidoDTO> adicionarItem(@PathVariable Integer pedidoID,
         @Valid @RequestBody DetalhePedidoDTO detalhePedidoDTO) {

        Pedido pedido = repository.findById(pedidoID)
                .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + pedidoID + " não encontrado"));

        DetalhesPedido detalhePedido = mapper.toDetalheEntity(detalhePedidoDTO);

        detalhePedido.setPedido(pedido);
        detalhePedido.setId(new DetalhePedidoID(pedidoID, detalhePedidoDTO.getIdProduto()));
        pedido.getDetalhesPedido().add(detalhePedido);      
        repository.save(pedido);
        DetalhePedidoDTO detalheSalvoDTO = mapper.toDetalheDto(detalhePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalheSalvoDTO);
    }

    @PutMapping("/{pedidoID}/itens/{produtoID}")
    public ResponseEntity<DetalhePedidoDTO> atualizarItem(@PathVariable Integer pedidoID,
         @PathVariable Integer produtoID, @Valid 
         @RequestBody DetalhePedidoDTO detalhePedidoDTO) {

        Pedido pedido = repository.findById(pedidoID)
                .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + pedidoID + " não encontrado"));
        DetalhePedidoID itemID = new DetalhePedidoID(pedidoID, produtoID);
        
        DetalhesPedido detalhePedido = mapper.toDetalheEntity(detalhePedidoDTO);
        detalhePedido.setId(itemID);
        detalhePedido.setPedido(pedido);
        pedido.getDetalhesPedido().removeIf(d -> d.getId().equals(itemID));
        pedido.getDetalhesPedido().add(detalhePedido);
        repository.save(pedido);

        return ResponseEntity.ok(mapper.toDetalheDto(detalhePedido));
    }

    @DeleteMapping("/{pedidoID}/itens/{produtoID}")
    public ResponseEntity<Void> deletarItem(@PathVariable Integer pedidoID, @PathVariable Integer produtoID) {
        Pedido pedido = repository.findById(pedidoID)
                .orElseThrow(() -> new EntityNotFoundException("Pedido de id " + pedidoID + " não encontrado"));
        DetalhePedidoID itemID = new DetalhePedidoID(pedidoID, produtoID);
        boolean removed = pedido.getDetalhesPedido().removeIf(d -> d.getId().equals(itemID));
        if (!removed) {
            throw new EntityNotFoundException("Item de id " + itemID + " não encontrado no pedido " + pedidoID);
        }
        repository.save(pedido);
        return ResponseEntity.noContent().build();
    }
}
