//
//  Style.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit

extension UIColor {

    convenience init(hexRed: CGFloat, hexGreen: CGFloat, hexBlue: CGFloat, hexAlpha: CGFloat = 255) {
        self.init(red: hexRed / 255, green: hexGreen / 255, blue: hexBlue / 255, alpha: hexAlpha / 255)
    }

    static var asosBlack: UIColor {
        return .init(hexRed: 45, hexGreen: 45, hexBlue: 45)
    }

    static var canteenYellow: UIColor {
        return .init(hexRed: 248, hexGreen: 231, hexBlue: 28)
    }

    static var payDisabledYellow: UIColor {
        return canteenYellow.withAlphaComponent(127 / 255)
    }
}

extension UIFont {
    private struct FontName {
        static let FuturaDemiReg = "FuturaPTDemi-Reg"
        static let FuturaBookReg = "FuturaPTBook-Reg"
        static let FutureHeavyReg = "FuturaPTHeavy-Reg"
        static let FutureMediumReg = "FuturaPTMedium-Reg"
        static let FuturaBoldReg = "FuturaPTBold-Reg"
    }

    static func futuraDemiReg(size: CGFloat) -> UIFont {
        return font(name: FontName.FuturaDemiReg, size: size)
    }

    static func futuraBookReg(size: CGFloat) -> UIFont {
        return font(name: FontName.FuturaBookReg, size: size)
    }

    static func futuraHeavyReg(size: CGFloat) -> UIFont {
        return font(name: FontName.FutureHeavyReg, size: size)
    }

    static func futuraMediumReg(size: CGFloat) -> UIFont {
        return font(name: FontName.FutureMediumReg, size: size)
    }

    static func futuraBoldReg(size: CGFloat) -> UIFont {
        return font(name: FontName.FuturaBoldReg, size: size)
    }

    private static func font(name: String, size: CGFloat) -> UIFont {
        return UIFont(name: name, size: size)!
    }
}
