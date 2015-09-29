package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Episode;

@Component
@Transactional
public class EpisodeToStringConverter implements Converter<Episode, String>{
	@Override
	public String convert(Episode source) {
		String result;
		
		if(source==null) 
			result=null;
		else 
			result=String.valueOf(source.getId());
		
		return result;
	}
}
