package com.jugnicaragua.demoappreactive.controlador;

import com.jugnicaragua.demoappreactive.modelo.Producto;
import com.jugnicaragua.demoappreactive.repositorio.ProductoRepository;
import com.jugnicaragua.demoappreactive.servicio.impl.ProductoServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductoRestController {

    private final ProductoRepository productoRepository;


    @GetMapping("/list-productos")
    public Flux<Producto> listProductos() {
        return this.productoRepository.findAll();
    }

    @GetMapping("/get-producto")
    public Mono<Producto> findById(@RequestParam("id") Long id) {
        return this.productoRepository.findById(id);
    }


    @PostMapping("/create-producto")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> createProducto(@RequestBody Producto producto) {
        return this.productoRepository.save(producto);
        		
    }

    @PutMapping("/modify-producto")
    public Mono<Producto> updateProducto(@RequestBody Producto producto) {
        return this.productoRepository
                .findById(producto.getId())
                .map(c->producto)
                .flatMap(this.productoRepository::save);
    }

    @DeleteMapping("/delete-producto")
    public Mono<Producto> deleteById(Long id){
        return this.productoRepository
                .findById(id)
                .flatMap(producto -> this.productoRepository.deleteById(producto.getId()).thenReturn(producto));
    }
}
