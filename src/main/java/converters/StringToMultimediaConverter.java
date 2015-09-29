package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.MultimediaRepository;
import domain.Multimedia;

@Component
@Transactional
public class StringToMultimediaConverter implements Converter<String, Multimedia>{
	@Autowired 
	private MultimediaRepository repository;

	@Override
	public Multimedia convert(String text) {
		Multimedia result;
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
