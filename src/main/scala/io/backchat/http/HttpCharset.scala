/*
 * Copyright (C) 2011 Mathias Doenitz
 * Based on code copyright (C) 2010-2011 by the BlueEyes Web Framework Team (http://github.com/jdegoes/blueeyes)
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

import java.nio.charset.Charset

sealed abstract class HttpCharsetRange {
  def value: String
  def matches(charset: HttpCharset): Boolean
  override def toString = "HttpCharsetRange(" + value + ')'
}

sealed abstract class HttpCharset extends HttpCharsetRange {
  lazy val nioCharset: Charset = Charset.forName(value)
  def aliases: Seq[String]
  def matches(charset: HttpCharset) = this == charset
  override def equals(obj: Any) = obj match {
    case x: HttpCharset ⇒ (this eq x) || (value eq x.value)
    case _              ⇒ false
  }
  override def hashCode() = value.##
  override def toString = "HttpCharset(" + value + ')'
}

// see http://www.iana.org/assignments/character-sets
object HttpCharsets extends ObjectRegistry[String, HttpCharset] {

  def register(charset: HttpCharset): HttpCharset = {
    register(charset, charset.value.toLowerCase)
    register(charset, charset.aliases.map(_.toLowerCase))
    charset
  }

  val `*` = new HttpCharsetRange {
    def value = "*"
    def matches(charset: HttpCharset) = true
  }

  private class PredefCharset(val value: String, val aliases: String*) extends HttpCharset

  val `US-ASCII` = register(new PredefCharset("US-ASCII", "iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ASCII", "ISO646-US", "us", "IBM367", "cp367", "csASCII"))
  val `ISO-8859-1` = register(new PredefCharset("ISO-8859-1", "iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "CP819", "csISOLatin1"))
  val `ISO-8859-2` = register(new PredefCharset("ISO-8859-2", "iso-ir-101", "ISO_8859-2", "latin2", "l2", "csISOLatin2"))
  val `ISO-8859-3` = register(new PredefCharset("ISO-8859-3", "iso-ir-109", "ISO_8859-3", "latin3", "l3", "csISOLatin3"))
  val `ISO-8859-4` = register(new PredefCharset("ISO-8859-4", "iso-ir-110", "ISO_8859-4", "latin4", "l4", "csISOLatin4"))
  val `ISO-8859-5` = register(new PredefCharset("ISO-8859-5", "iso-ir-144", "ISO_8859-5", "cyrillic", "csISOLatinCyrillic"))
  val `ISO-8859-6` = register(new PredefCharset("ISO-8859-6", "iso-ir-127", "ISO_8859-6", "ECMA-114", "ASMO-708", "arabic", "csISOLatinArabic"))
  val `ISO-8859-7` = register(new PredefCharset("ISO-8859-7", "iso-ir-126", "ISO_8859-7", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek"))
  val `ISO-8859-8` = register(new PredefCharset("ISO-8859-8", "iso-ir-138", "ISO_8859-8", "hebrew", "csISOLatinHebrew"))
  val `ISO-8859-9` = register(new PredefCharset("ISO-8859-9", "iso-ir-148", "ISO_8859-9", "latin5", "l5", "csISOLatin5"))
  val `ISO-8859-10` = register(new PredefCharset("ISO-8859-1", "iso-ir-157", "l6", "ISO_8859-10", "csISOLatin6", "latin6"))
  val `UTF-8` = register(new PredefCharset("UTF-8", "UTF8"))
  val `UTF-16` = register(new PredefCharset("UTF-16", "UTF16"))
  val `UTF-16BE` = register(new PredefCharset("UTF-16BE"))
  val `UTF-16LE` = register(new PredefCharset("UTF-16LE"))
  val `UTF-32` = register(new PredefCharset("UTF-32", "UTF32"))
  val `UTF-32BE` = register(new PredefCharset("UTF-32BE"))
  val `UTF-32LE` = register(new PredefCharset("UTF-32LE"))

  class CustomHttpCharset(_value: String, val aliases: Seq[String] = Nil) extends HttpCharset {
    val value = _value.toLowerCase.intern()
  }
}
