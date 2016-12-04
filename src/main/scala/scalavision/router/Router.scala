package scalavision.router


import org.scalajs.dom.raw.{HashChangeEvent, Node}
import rx.{Ctx, Var, _}
import org.scalajs.dom


import scalatags.JsDom.all._

trait Page extends Product with Serializable {
  def urlName: String
  def linkLabel: String
  def content: Frag
  val hashUrl = Router.topUrl + urlName
}

case class StaticPage(
  urlName: String,
  linkLabel: String,
  content: Frag
)(implicit ctx: Ctx.Owner)
  extends Page

case class ActionPage(
  urlName: String,
  linkLabel: String,
  content: Frag,
  onExit: () => Unit,
  onEnter: () => Unit
)( implicit ctx: Ctx.Owner)
  extends Page

trait HomeTemplate {
  def onEnter(): Unit
  def onExit() : Unit
  def view() : Frag
}

object Router {
  val topUrl = "#/"
  val url:String = "http://" + dom.window.location.host
  val currentPageNode = Var[dom.raw.Node](null)
  val hashChangeEvent = Var[HashChangeEvent](null)
  val currentHashUrl = Var[String](topUrl)
  type HashUrl = String

  dom.window.onhashchange = {
    (e: HashChangeEvent) =>
      hashChangeEvent.update(e)
  }

  def navigateToUrl(hashUrl:HashUrl):Unit =
    dom.window.location.hash = hashUrl

  def goHome():Unit =
    dom.window.location.hash = topUrl

  private def replacePage(root: Node, newChild: Frag):Unit = {
    val newPage = newChild.render
    root.replaceChild(newPage, currentPageNode.now)
    currentPageNode.update(newPage)
  }


  // The url's after # for local routing inside a component
  def urlsAfterHash: Vector[String] = ???

  def apply(
    pages: Seq[Page],
    homePage: HomeTemplate,
    root: Node
  )(
    implicit ctx: Ctx.Owner
  ):Map[String, Page] = {

    val home = homePage.view().render

    currentPageNode.update(home)
    root.appendChild(
      home
    )

    val site:Map[String, Frag]=
      pages.map( page =>
        page.hashUrl -> page.content
      ).toMap

    val pageMap: Map[String, Page] =
      pages.map( page =>
        page.hashUrl -> page
      ).toMap

    hashChangeEvent.trigger {
      val hashChangeEventTmp = hashChangeEvent.now
      val hash = dom.window.location.hash

      if(hashChangeEventTmp != null) {
        val newUrl = hashChangeEventTmp.newURL
        val oldUrl = hashChangeEventTmp.oldURL

        if(hash == topUrl || hash.isEmpty) {
          homePage.onEnter()
        }

        if(newUrl != oldUrl) {
          val oldUrlPath = oldUrl.split("#")
          val newUrlPath = newUrl.split("#")

          pageMap.get("#" + oldUrlPath.last).map {
            case a@ActionPage(_,_,_,_,_ )=>
              a.onExit
            case _ =>
            // do nothing
          }

          pageMap.get("#" + newUrlPath.last).map {
            case a@ActionPage(_,_,_,_,_) =>
              a.onEnter
            case _ =>
            // do nothing
          }

          if(hash == topUrl)
            replacePage(root, home)
          else {
            site.get(hash) match {
              case None =>
                replacePage(root, homePage.view())
              case Some(page) =>
                replacePage(root, page)
            }
          }
        }

      }
      else {
        val newPage = site.getOrElse(hash, homePage.view())
        replacePage(root, newPage)
      }
    }

    pageMap
  }

}