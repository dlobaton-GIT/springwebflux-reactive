package com.jugnicaragua.demoappreactive.servicio;
import com.jugnicaragua.demoappreactive.modelo.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IProductoService {
    public Flux<Producto> findAll();

    public Mono<Producto> findById(Long id);

    public Mono<Producto> save(Producto producto);
 
    public Mono<Void> deleteById(Long id);
}
