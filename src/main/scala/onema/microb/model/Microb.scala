/**
  * This file is part of the ONEMA ServerlessApiMeetup Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2018, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <kinojman@gmail.com>
  */

package onema.microb.model

object Microb {
  case class Item(title: String, content: String, date: String = null, id: String = null)
  case class ItemList(posts: Seq[Item], count: Int, next: String = "")
}
