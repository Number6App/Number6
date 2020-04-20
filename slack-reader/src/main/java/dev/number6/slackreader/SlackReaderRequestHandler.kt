package dev.number6.slackreader

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import dev.number6.slackreader.dagger.DaggerSlackReaderComponent

class SlackReaderRequestHandler : RequestHandler<Map<String, Any>, String> {
    private val handler = DaggerSlackReaderComponent.create().handler()
    override fun handleRequest(o: Map<String, Any>, context: Context): String {
        handler.handle(o, context.logger)
        return "ok"
    }
}