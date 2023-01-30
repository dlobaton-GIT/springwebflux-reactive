package com.jugnicaragua.demoappreactive.controlador;
import com.jugnicaragua.demoappreactive.repositorio.ProductoRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserters;

import com.jugnicaragua.demoappreactive.modelo.Producto;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductoRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    
    //PodamFactory factory = new PodamFactoryImpl();
    @Autowired
    ConnectionFactory cf;
    @BeforeEach
    public void setUp() {
        Flux.from(cf.create())
                .flatMap(c ->
                        c.createBatch()
                                .add("DROP TABLE IF EXISTS productos;")
                                .add("CREATE TABLE productos ( id SERIAL PRIMARY KEY, descripcion VARCHAR(100) NOT NULL, nombre VARCHAR(40) NOT NULL, estado VARCHAR(40) NOT NULL, precio NUMERIC(6, 2));")
                                .add("insert into productos(descripcion,nombre,estado, precio) values ( 'Telefono', 'Iphone 8', 'Nuevo', 100 ) ")
                                .add("insert into productos(descripcion,nombre,estado, precio) values ( 'Tablet', 'Ipad 6', 'OK', 100) ")
                                .execute()
                )
                .log()
                .blockLast();
    }
    @Test
    public void createProducto() {
        //Producto producto = factory.manufacturePojo(Producto.class);
        Producto producto = Producto.builder()
                .id(null)
                .nombre("Huawei")
                .descripcion("Tfno")
                .estado("Nuevo")
                .precio(new BigDecimal(200))
                .build();
        webTestClient
            .post()
            .uri("/create-producto")
            .body(BodyInserters.fromValue(producto)) // !! Esto con un Pojo no funciona
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(Producto.class)
            .value((res) ->
                assertNotNull(res.getNombre())
            );

    }

    @Test
    public void getProductos() {
        webTestClient
                .get()
                .uri("/list-productos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class)
                .value((res) -> {
                    assertEquals(2, res.size());
                });
    }

    @Test
    public void getProducto() {
        webTestClient
                .get()
                .uri("/get-producto?id=1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Producto.class)
                .value((res) -> {
                    assertEquals(1, res.getId());
                });
    }

    @Test
    void updateProducto() {
        Producto aux = Producto.builder()
                .id(2L)
                .nombre("Aux")
                .descripcion("Tfno")
                .estado("Nuevo")
                .precio(new BigDecimal(700))
                .build();

        webTestClient
                .put()
                .uri("/modify-producto")
                .body(BodyInserters.fromValue(aux))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Producto.class)
                .value((res) ->
                        assertEquals("Nuevo", res.getEstado())
                );
    }

    @Test
    void deleteProducto() {
        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/delete-producto").queryParam("id",3).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}