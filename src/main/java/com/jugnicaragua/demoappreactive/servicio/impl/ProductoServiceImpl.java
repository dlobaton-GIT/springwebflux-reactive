package com.jugnicaragua.demoappreactive.servicio.impl;

import com.jugnicaragua.demoappreactive.modelo.Producto;
import com.jugnicaragua.demoappreactive.repositorio.ProductoRepository;
import com.jugnicaragua.demoappreactive.servicio.IProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepositorio;

    @Override
    public Flux<Producto> findAll() {
        return productoRepositorio.findAll();
    }
    @Override
    public Mono<Producto> findById(Long id) {
        return productoRepositorio.findById(id);
    }
    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepositorio.save(producto);
    }
    @Override
    public Mono<Void> deleteById(Long id){
        return this.productoRepositorio.findById(id)
                .flatMap(this.productoRepositorio::delete);
    }
    
}
