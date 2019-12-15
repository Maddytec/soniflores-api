package br.com.maddytec.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class Converter {

	public static <D, S> D dePara(Object origem, Class<D> destinoTipo) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(origem.getClass(), destinoTipo.getClass());
		MapperFacade mapper = mapperFactory.getMapperFacade();
		D destino = mapper.map(origem, destinoTipo);

		return destino;
	}

}
