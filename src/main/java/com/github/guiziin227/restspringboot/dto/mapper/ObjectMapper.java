package com.github.guiziin227.restspringboot.dto.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    // Mapper é uma interface do Dozer que fornece métodos para mapear entre objetos
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    //De entidade para DTO e vice-versa
                                    //Ex: Person -> PersonDTO.class
    public static <O,D> D parseObject(O origin, Class<D> destinationClass) {
        return mapper.map(origin, destinationClass);
    }

    //De DTO para entidade e vice-versa com lista
    public static <O,D> List<D> parseListObjects(List<O> origin, Class<D> destinationClass) {
        // Cria uma nova lista do tipo D para armazenar os objetos mapeados
        List<D> destination = new ArrayList<D>();
        // Itera sobre cada objeto da lista de origem e mapeia para o tipo de destino
        for (O o : origin) {
            destination.add(mapper.map(o, destinationClass));
        }

        return destination;
    }
}
