package dispatch.github

import dispatch._
import json._
import JsHttp._

import java.util.Date

case class GhContributor(id: Int, login: String, avatar_url: String)

case class GhAuthorSummary(name:String, date:Date, email:String)

case class GhAuthor(avatar_url: String, url: String, login: String, gravatar_id: String, id: Int)

case class GhUser(id: Int, login: String, name: String, email: String, avatar_url: String, account_type: String)


object GhUser {
	
	def get_authenticated_user(access_token: String) = {
		val svc = GitHub.api_host / "user"
		svc.secure <<? Map("access_token" -> access_token) ># { json =>
			val jsonObj = parse.jsonObj(json)
			
			val id = jsonObj("id").asInt
			val login = jsonObj("login").asString
			val name = if (jsonObj.contains("name")) jsonObj("name").asString else ""
			val email = jsonObj("email").asString
			val avatar_url = jsonObj("avatar_url").asString
			val account_type = jsonObj("type").asString
			
			GhUser(id, login, name, email, avatar_url, account_type)
		}
	}
		
}