package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProducerCompany;

@Component
@Transactional
public class ProducerCompanyToStringConverter implements Converter<ProducerCompany, String>{
	@Override
	public String convert(ProducerCompany source) {
		String result;
		
		if(source==null) 
			result=null;
		else 
			result=String.valueOf(source.getId());
		
		return result;
	}
}
