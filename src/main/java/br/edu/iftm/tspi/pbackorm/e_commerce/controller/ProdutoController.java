package br.edu.iftm.tspi.pbackorm.e_commerce.controller;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Produto;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutosPorPedidoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.ProdutoMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.repository.ProdutoRepository;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.UnidadesCompradasDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {
 
     private final ProdutoRepository repository;
    
     private final ProdutoMapper mapper;
     
     @PostMapping
     public ResponseEntity<ProdutoDTO> novo (@Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produtoConvertido = mapper.toEntity(produtoDTO);
        Produto produtoSalvo = repository.save(produtoConvertido);
        ProdutoDTO produtoDtoRetorno = mapper.toDto(produtoSalvo);
         return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(produtoDtoRetorno);

     }
     @GetMapping
     public ResponseEntity<List<ProdutoDTO>>
                                   listar(@RequestParam(required = false) String nome,
                                   @RequestParam(required = false) Short estoque,
                                   @RequestParam(required = false) Double preco){
        nome = (nome == null) ? "" :nome;
        estoque = (estoque == null)? Short.MIN_VALUE : estoque;
        preco = (preco == null)? Double.MAX_VALUE: preco;
        List<Produto> produtos = repository.
                                findByNomeContainingIgnoreCaseAndEstoqueGreaterThanEqualAndPrecoLessThanEqual(
                                    nome, estoque, preco);
             return ResponseEntity.ok().body(mapper.toDtoList(produtos));
   
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Integer id){
     Produto produto = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException
                    ("Produto de ID "+id+" não encontrado"));
     ProdutoDTO produtoDtoRetorno = mapper.toDto(produto);
     return ResponseEntity.ok().body(produtoDtoRetorno);
    }
     
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Integer id,
                                       @Valid @RequestBody ProdutoDTO produtoAtualizar){
        if(!repository.existsById(id)){
          throw new EntityNotFoundException("Produto de ID "+id+" não encontrado");
        }
        Produto produtoConvertido = mapper.toEntity(produtoAtualizar);
        produtoConvertido.setId(id);
        Produto produtoAtualizado = repository.save(produtoConvertido);
        ProdutoDTO produtoDtoRetorno = mapper.toDto(produtoAtualizado);
        return ResponseEntity.ok(produtoDtoRetorno);
    }
     
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
     Produto produto = repository.findById(id)
                       .orElseThrow(()-> new EntityNotFoundException
                       ("Produto de id "+id+" não encontrado"));
       repository.delete(produto);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("maior-preco-por-categoria")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosComMaiorPrecoPorCategoria() {
         List<ProdutoDTO> produtos = repository.buscarProdutosComMaiorPrecoPorCategoria();
         if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
         }
         return ResponseEntity.ok(produtos);
    }

    @GetMapping("maior-preco-por-categoria-nativo/{id}")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosComMaiorPrecoPorCategoriaNativo
                        (@PathVariable("id") Integer id) {
         List<ProdutoDTO> produtos = repository.buscarProdutosComMaiorPrecoPorCategoriaNativo(id);
         if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(produtos);
    }

    @GetMapping("unidades-compradas-periodo")
    public ResponseEntity<List<UnidadesCompradasDTO>> buscarUnidadesCompradasPeriodo
                        (@RequestParam(required = true) LocalDate dataInicio,
                         @RequestParam(required = true) LocalDate dataFim) {
         List<UnidadesCompradasDTO> produtos = repository.findUnidadesCompradasPeriodo(dataInicio,dataFim);
         if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(produtos);
    }

      @GetMapping("unidades-compradas-periodoJPQL")
    public ResponseEntity<List<UnidadesCompradasDTO>> buscarUnidadesCompradasPeriodoJPQL
                        (@RequestParam(required = true) LocalDateTime dataInicio,
                         @RequestParam(required = true) LocalDateTime dataFim) {
         List<UnidadesCompradasDTO> produtos = repository.findUnidadesCompradasPeriodoJPQL(dataInicio,dataFim);
         if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(produtos);
    }

    @GetMapping("unidades-compradas-periodo-produto")
    public ResponseEntity<List<UnidadesCompradasDTO>> buscarUnidadesCompradasPeriodoProduto
                        (@RequestParam(required = true) LocalDateTime dataInicio,
                         @RequestParam(required = true) LocalDateTime dataFim,
                         @RequestParam(required = false) Integer id) {
         List<UnidadesCompradasDTO> produtos = repository.findbyBuscarUnidadeCompradaPorIdParametroIdNaoObrigatorio(dataInicio, dataFim, id);
         if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(produtos);
    }

    @GetMapping("/pedidos-por-produto/{id}/pedidos")
    public ResponseEntity<List<ProdutosPorPedidoDTO>> buscarProdutosPorPedido(@PathVariable Integer id) {
        List<ProdutosPorPedidoDTO> produtosPorPedido = repository.findbyRetornarPedidosPorIdProduto(id);
        if (produtosPorPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtosPorPedido);
    }
}
