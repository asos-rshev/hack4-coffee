//
//  Storyboard+Extensions.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

extension UIStoryboard {

    var _mainNavigation: MainNavigationController {
        return instantiateViewController(withIdentifier: "MainNavigationController") as! MainNavigationController
    }

    var _itemsList: ItemsListViewController {
        return instantiateViewController(withIdentifier: "ItemsListViewController") as! ItemsListViewController
    }
}
