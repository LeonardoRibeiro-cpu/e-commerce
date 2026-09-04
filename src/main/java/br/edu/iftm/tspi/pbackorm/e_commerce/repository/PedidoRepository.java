package br.edu.iftm.tspi.pbackorm.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    
    List<Pedido> findByClienteNomeContainingIgnoreCase(String nome);
    List<Pedido> findByClienteId(String clienteId);
}
