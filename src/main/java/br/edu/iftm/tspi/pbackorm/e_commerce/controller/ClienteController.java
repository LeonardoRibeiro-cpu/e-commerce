package br.edu.iftm.tspi.pbackorm.e_commerce.controller;

import java.time.LocalDateTime;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Cliente;
import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Pedido;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ClienteDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.PedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoPorIdClienteDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.PedidosProdutosDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.ClienteMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.PedidoMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.repository.ClienteRepository;
import br.edu.iftm.tspi.pbackorm.e_commerce.repository.PedidoRepository;
import br.edu.iftm.tspi.pbackorm.e_commerce.utils.GeradorID;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {
    
    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    @GetMapping
    public List<ClienteDTO> listar(@RequestParam(required = false) String cidade,
                                    @RequestParam(required = false) String pais,
                                    @RequestParam(required = false) String nome,
                                     @RequestParam(required = false) String id) {

     List<Cliente> clientes;
     if(!(cidade == null)) {
        clientes = repository.findByCidade(cidade);
     } else if (!(pais == null)) {
        clientes = repository.findByPais(pais);
     } else if (!(nome == null)) {
        clientes = repository.findByNomeContainingIgnoreCase(nome);
     } else if (!(id == null)) {
        clientes = repository.findById(id)
                .map(List::of)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de id " + id + " não encontrado"));
     } else {
        clientes = repository.findAll(); 
  
     }
        return mapper.toDtoList(clientes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable String id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de id " + id + " não encontrado"));
        ClienteDTO clienteDTO = mapper.toDto(cliente);
        return ResponseEntity.ok().body(clienteDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteDTO clienteDTO) {
        String id;
       int tentativas = 0;
          
       do{
          id = GeradorID.gerarID();
          tentativas++;
            if(tentativas > 50) {
                 throw new RuntimeException("Não foi possível gerar um ID único para o cliente após 5 tentativas");
            }
       }while(repository.existsById(id));

        Cliente cliente = mapper.toEntity(clienteDTO);
        cliente.setId(id);
        Cliente clienteSalvo = repository.save(cliente);
        ClienteDTO clienteSalvoDTO = mapper.toDto(clienteSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvoDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable String id, @Valid @RequestBody ClienteDTO clienteDTO) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Cliente de id " + id + " não encontrado");
        }
        Cliente cliente = mapper.toEntity(clienteDTO);
        cliente.setId(id);
        Cliente clienteAtualizado = repository.save(cliente);
        ClienteDTO clienteAtualizadoDTO = mapper.toDto(clienteAtualizado);
        return ResponseEntity.ok().body(clienteAtualizadoDTO);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Cliente de id " + id + " não encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clienteId}/pedidos")
     public ResponseEntity<List<PedidoDTO>> listarPedidos(@PathVariable String clienteId) {
        if(!repository.existsById(clienteId)) {
            throw new EntityNotFoundException("Cliente de id " + clienteId + " não encontrado");
        }

        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        List<PedidoDTO> pedidosDTO = pedidoMapper.toDtoList(pedidos);
        return ResponseEntity.ok().body(pedidosDTO);
    }

    @GetMapping("/{clienteId}/produtos")
    public ResponseEntity<List<ProdutoPorIdClienteDTO>> listarProdutosPorCliente(@PathVariable String clienteId) {
        if(!repository.existsById(clienteId)) {
            throw new EntityNotFoundException("Cliente de id " + clienteId + " não encontrado");
        }

        List<ProdutoPorIdClienteDTO> produtosDTO = repository.findProdutosById(clienteId);
        return ResponseEntity.ok().body(produtosDTO);
    }


     @GetMapping("/{clienteId}/pedidos-por-data")
     public ResponseEntity<List<PedidosProdutosDTO>> buscarPedidos(
             @PathVariable String clienteId,
             @RequestParam LocalDateTime dataInicio,
             @RequestParam LocalDateTime dataFim
       ) {
         List<PedidosProdutosDTO> lista =
            repository.buscarPedidosComProdutos(clienteId, dataInicio, dataFim);

         return ResponseEntity.ok(lista);
         }         
}