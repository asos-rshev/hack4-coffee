//
//  RoundView.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 01/12/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

final class RoundView: UIView {

    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = bounds.width / 2
    }
}
