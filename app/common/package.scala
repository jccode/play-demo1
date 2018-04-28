package object common {

  object Implics extends Migrate
    with JsonFormater

  object utils extends PlayUtils

  object migrate extends Migrate
}
