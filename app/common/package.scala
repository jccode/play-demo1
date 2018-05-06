package object common {

  object Implics extends Migrate
    with JsonFormater

  object utils extends PlayUtils with CommonUtils

  object migrate extends Migrate
}
