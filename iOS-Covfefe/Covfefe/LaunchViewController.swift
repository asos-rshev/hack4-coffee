//
//  LaunchViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

final class LaunchViewController: UIViewController {

    @IBOutlet var spinnerView: UIActivityIndicatorView!

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

}
