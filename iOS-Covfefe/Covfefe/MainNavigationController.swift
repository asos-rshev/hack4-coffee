//
//  MainNavigationController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

final class MainNavigationController: UINavigationController {

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        navigationBar.tintColor = .canteenYellow
    }

    static var logoImageView: UIImageView {
        let imageView = UIImageView(image: #imageLiteral(resourceName: "LOGO"))
        imageView.contentMode = .scaleAspectFit
        return imageView
    }

    override func pushViewController(_ viewController: UIViewController, animated: Bool) {
        viewController.navigationItem.titleView = MainNavigationController.logoImageView
        viewController.navigationItem.title = " "

        super.pushViewController(viewController, animated: animated)
    }
}
