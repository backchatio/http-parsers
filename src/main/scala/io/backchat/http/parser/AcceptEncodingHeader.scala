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
import BasicRules._
import HttpEncodings._

private[parser] trait AcceptEncodingHeader {
  this: Parser with ProtocolParameterRules ⇒

  def ACCEPT_ENCODING = rule (
    oneOrMore(EncodingRangeDecl, separator = ListSep) ~ EOI ~~> (HttpHeaders.`Accept-Encoding`(_)))

  def EncodingRangeDecl = rule (
    EncodingRangeDef ~ optional(EncodingQuality))

  def EncodingRangeDef = rule (
    "*" ~ push(`*`)
      | ContentCoding ~~> (x ⇒ getForKey(x.toLowerCase).getOrElse(new CustomHttpEncoding(x))))

  def EncodingQuality = rule {
    ";" ~ "q" ~ "=" ~ QValue // TODO: support encoding quality
  }

}