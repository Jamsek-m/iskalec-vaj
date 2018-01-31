package services.templateEngine;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;

@ApplicationScoped
public class ThymeleafEngine {
	
	private TemplateEngine templateEngine;
	
	@PostConstruct
	private void init() {
		this.templateEngine = templateEngine();
	}
	
	private TemplateEngine templateEngine() {
		TemplateEngine templEng = new TemplateEngine();
		templEng.addTemplateResolver(htmlTemplateResolver());
		return templEng;
	}
	
	private ITemplateResolver htmlTemplateResolver() {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setOrder(1);
		resolver.setResolvablePatterns(Collections.singleton("html/*"));
		resolver.setPrefix("/mail/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("utf-8");
		resolver.setCacheable(false);
		return resolver;
	}
	
	public TemplateEngine getTemplateEngine() {
		return this.templateEngine;
	}
}
