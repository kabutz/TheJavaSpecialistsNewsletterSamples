class Insect {
    init() {
        print("Inside Insect() Constructor")
        printDetails()
    }

    func printDetails() {
        print("Just an insect")
    }
}

class Beetle: Insect {
    private let legs: Int

    init(legs: Int) {
        self.legs = legs // setting our field before super.init()
        super.init()
        print("Inside Beetle() Constructor")
    }

    override func printDetails() {
        print("The beetle has \(legs) legs")
        if legs < 6 {
            print("Ouch")
        }
    }
}

// lost one leg in an argument with his wife
let sadBug = Beetle(legs: 5)
// the wife bug ;-)
let happyBug = Beetle(legs: 6)
