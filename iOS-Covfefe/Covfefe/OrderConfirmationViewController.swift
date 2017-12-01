//
//  OrderConfirmationViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 01/12/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

class OrderConfirmationViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.hidesBackButton = true
    }

    @IBAction func didTap(_ sender: Any) {
        navigationController?.popToRootViewController(animated: true)
    }
}
