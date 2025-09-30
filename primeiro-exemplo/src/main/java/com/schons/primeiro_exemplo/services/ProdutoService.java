package com.schons.primeiro_exemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schons.primeiro_exemplo.model.Produto;
import com.schons.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.schons.primeiro_exemplo.repository.ProdutoRepository;
import com.schons.primeiro_exemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Metodo para retornar uma lista de produtos.
     * @return Lista de produtos.
     */
    public List<ProdutoDTO> obterTodos(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());
    }

    /**
     * Metodo que retorna o produto encontrado pelo seu id.
     * @param id do produto que sera localizado.
     * @return Retorna um produto caso seja encontrado.
     */
     public Optional<ProdutoDTO> obterPorId(Integer id){
        //Obtendo optional de produto pelo id.
        Optional<Produto> produto =  produtoRepository.findById(id);
        //se nao encontrar lanca exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto com id:" + id + " nao encontrado!");
        }
        //convertendo meu optional de produto em um produto dto
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
        //Criando e retornando um optional de produto dto
        return Optional.of(dto);
    }

     /**
     *Metodo para adicionar produto na lista. 
     * @param produto que sera adicionado.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto){
        //Removendo o id para conseguir fazer o cadastro
        produtoDto.setId(null);
        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();
        //Converter nosso produtoDTO em um Produto
        Produto produto = mapper.map(produtoDto, Produto.class);
        //Salvar o produto no banco
        produto = produtoRepository.save(produto);
        produtoDto.setId(produto.getId());
        //Retornar o ProdutoDTO atualizado
        return produtoDto;
    }

    /**
     * Metodo para deletar o produto por id.
     * @param id do produto a ser deletado.
     */
    public void deletar(Integer id){
        //verificar se o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);
        //Se nao encontrar o produto lanca uma exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Nao foi possivel deletar o produto com o ID: " + id);
        }
        //Deleta o produto pelo id
        produtoRepository.deleteById(id);
    }

    /**
     * Metodo para atualizar o produto na lista.
     * @param produto que sera atualizado.
     * @param id do produto
     * @return Retorna o produto atualizado.
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
        //Passar o id para o produtoDto
        produtoDto.setId(id);
        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();
        //Converter o DTO em um produto
        Produto produto = mapper.map(produtoDto, Produto.class);
        //Atualizar o produto no banco
        produtoRepository.save(produto);
        //Retorna o produtoDTO atualizado;
        return produtoDto;
    }   
}
