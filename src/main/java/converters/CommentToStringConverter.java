package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Comment;

@Component
@Transactional
public class CommentToStringConverter implements Converter<Comment, String>{
	@Override
	public String convert(Comment source) {
		String result;
		
		if(source==null) 
			result=null;
		else 
			result=String.valueOf(source.getId());
		
		return result;
	}
}
