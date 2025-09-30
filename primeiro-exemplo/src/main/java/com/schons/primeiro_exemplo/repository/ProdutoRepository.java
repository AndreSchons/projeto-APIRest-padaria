package com.schons.primeiro_exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schons.primeiro_exemplo.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
}
