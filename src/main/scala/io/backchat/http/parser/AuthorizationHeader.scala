/*
 * Copyright (C) 2011 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.backchat.http
package parser

import org.parboiled.scala._
import org.parboiled.common.Base64
import BasicRules._

private[parser] trait AuthorizationHeader {
  this: Parser with ProtocolParameterRules with AdditionalRules =>

  def AUTHORIZATION = rule {
    CredentialDef ~~> HttpHeaders.`Authorization` 
  }
  
  def CredentialDef = rule {
    BasicCredentialDef | OtherCredentialDef
  }
  
  def BasicCredentialDef = rule {
    "Basic" ~ BasicCookie ~> (BasicHttpCredentials(_))
  }
  
  def BasicCookie = rule {
    oneOrMore(anyOf(Base64.rfc2045.getAlphabet))
  }
  
  def OtherCredentialDef = rule (
    AuthScheme ~ zeroOrMore(AuthParam, separator = ListSep)
        ~~> ((scheme, params) => OtherHttpCredentials(scheme, params.toMap))
  )

}