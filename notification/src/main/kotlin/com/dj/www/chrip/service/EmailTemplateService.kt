package com.dj.www.chrip.service

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailTemplateService(
    private val templateEngine: TemplateEngine,
) {
    fun processTemplate(
        templateName: String,
        variables: Map<String, Any> = emptyMap()
    ): String { // returns raw html
        val context = Context().apply {
            variables.forEach { (key, value) ->
                setVariable(key, value)
            }
        }
        return templateEngine.process(templateName, context)
    }
}