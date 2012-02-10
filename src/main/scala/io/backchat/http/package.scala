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

package io.backchat

import collection.LinearSeq

package object http {

  //  type HttpResult[+T] = Either[HttpException, T]

  /**
   * Warms up the io.backchat.http module by triggering the loading of most classes in this package,
   * so as to increase the speed of the first usage.
   */
  def warmUp() {
    List(
      HttpHeader("Accept", "*/*,text/plain,custom/custom"),
      HttpHeader("Accept-Charset", "*,UTF-8,custom"),
      HttpHeader("Accept-Encoding", "gzip,custom"),
      HttpHeader("Accept-Language", "*,en-gb,custom"),
      HttpHeader("Cache-Control", "no-cache"),
      HttpHeader("Connection", "close"),
      HttpHeader("Cookie", "backchat=cool"),
      HttpHeader("Content-Encoding", "deflate"),
      HttpHeader("Content-Length", "42"),
      HttpHeader("Content-Type", "application/json"),
      HttpHeader("Fancy-Custom-Header", "yeah"))
    HttpContent("backchat rocks!")
    HttpIp.fromString("127.0.0.1")
  }

  def identityFunc[T <: Any] = (x: T) ⇒ x

  implicit def seq2httpParserLinearSeq[A](coll: LinearSeq[A]) = new HttpParserLinearSeq[A](coll)

}