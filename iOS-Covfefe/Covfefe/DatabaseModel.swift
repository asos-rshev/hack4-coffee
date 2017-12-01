//
//  DatabaseModel.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import FirebaseDatabase

final class DatabaseModel {

    private enum Constant {
        static let retryInterval: TimeInterval = 5
        static let menuKey = "menu"
        static let milksKey = "milks"
    }

    let firebaseRef: DatabaseReference = Database.database().reference()

    var menu: [Category] = []
    var milks: [Milk] = []

    typealias Completion = (_ success: Bool) -> Void

    func refreshFromRemote(completion: @escaping Completion) {
        firebaseRef.observeSingleEvent(of: .value, with: { [weak self] (dataSnapshot) in
            self?.parse(dataSnapshot: dataSnapshot, completion: completion)
        }) { [weak self] _ in
            DispatchQueue.global().asyncAfter(deadline: .now() + Constant.retryInterval, execute: {
                self?.refreshFromRemote(completion: completion)
            })
        }
    }

    private func parse(dataSnapshot: DataSnapshot, completion: @escaping Completion) {
        guard
            let rootDictionary = dataSnapshot.value as? NSDictionary,
            let menuDictionary = rootDictionary[Constant.menuKey] as? NSArray,
            let milksDictionary = rootDictionary[Constant.milksKey] as? NSArray,
            let menuJson = try? JSONSerialization.data(withJSONObject: menuDictionary, options: []),
            let milksJson = try? JSONSerialization.data(withJSONObject: milksDictionary, options: [])
        else {
            completion(false)
            return
        }

        let decoder = JSONDecoder()

        do {
            menu = try decoder.decode([Category].self, from: menuJson)
            milks = try decoder.decode([Milk].self, from: milksJson)
            completion(true)
        } catch (let error) {
            print(error)
            completion(false)
        }
    }
}

struct Category: Codable {
    typealias Kind = Int

    var name: String
    var type: Kind
    var items: [Item]
}

struct Item: Codable {
    typealias Kind = Int

    var name: String
    var milky: Bool
    var type: Kind
    var sizes: [NameAndPrice]
    var extras: [NameAndPrice]?
}

struct NameAndPrice: Codable {
    var name: String
    var price: Decimal
}

struct Milk: Codable {
    typealias Kind = Int

    var name: String
    var type: Kind
}
