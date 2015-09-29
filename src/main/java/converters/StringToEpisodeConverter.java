package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.EpisodeRepository;
import domain.Episode;

@Component
@Transactional
public class StringToEpisodeConverter implements Converter<String, Episode>{
	@Autowired 
	private EpisodeRepository repository;

	@Override
	public Episode convert(String text) {
		Episode result;
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
