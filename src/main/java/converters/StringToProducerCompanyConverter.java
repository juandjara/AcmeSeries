package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.ProducerCompanyRepository;
import domain.ProducerCompany;

@Component
@Transactional
public class StringToProducerCompanyConverter implements Converter<String, ProducerCompany>{
	@Autowired 
	private ProducerCompanyRepository repository;

	@Override
	public ProducerCompany convert(String text) {
		ProducerCompany result;
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
