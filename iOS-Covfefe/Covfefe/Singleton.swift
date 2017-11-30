//
//  Singleton.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import Foundation
import Firebase

final class Singleton {

    static let shared = Singleton()

    let databaseModel = DatabaseModel()
}
