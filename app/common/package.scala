package object common {

  object Implics extends Migrate
    with JsonFormater

  object utils extends CommonUtils

  object migrate extends Migrate
}
