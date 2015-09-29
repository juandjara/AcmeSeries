package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mark;

@Component
@Transactional
public class MarkToStringConverter implements Converter<Mark, String>{
	@Override
	public String convert(Mark source) {
		String result;
		
		if(source==null) 
			result=null;
		else 
			result=String.valueOf(source.getId());
		
		return result;
	}
}
