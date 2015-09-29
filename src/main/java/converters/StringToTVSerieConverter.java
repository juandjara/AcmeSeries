package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.TVSerieRepository;
import domain.TVSerie;

@Component
@Transactional
public class StringToTVSerieConverter implements Converter<String, TVSerie>{
	@Autowired 
	private TVSerieRepository repository;

	@Override
	public TVSerie convert(String text) {
		TVSerie result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=repository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
