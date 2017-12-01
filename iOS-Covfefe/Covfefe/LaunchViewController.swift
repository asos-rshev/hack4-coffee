//
//  LaunchViewController.swift
//  Covfefe
//
//  Created by Roman Shevtsov on 30/11/2017.
//  Copyright © 2017 ASOS. All rights reserved.
//

import UIKit
import FirebaseAuthUI
import FirebaseGoogleAuthUI

final class LaunchViewController: UIViewController {

    @IBOutlet var spinnerView: UIActivityIndicatorView!

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        _ = Auth.auth().addStateDidChangeListener { [weak self] (authState, user) in
            if user == nil {
                self?.tryToAuthenticate()
            } else {
                self?.requestDatabase()
            }
        }
    }

    private func tryToAuthenticate() {
        let authUI = FUIAuth.defaultAuthUI()
        authUI?.delegate = self

        let providers: [FUIAuthProvider] = [
            FUIGoogleAuth(),
        ]
        authUI?.providers = providers

        guard let authViewController = authUI?.authViewController() else {
            DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: { [weak self] in
                self?.tryToAuthenticate()
            })
            return
        }
        present(authViewController, animated: true, completion: nil)
    }

    private func requestDatabase() {
        Singleton.shared.databaseModel.refreshFromRemote { [weak self] (success) in
            guard
                success,
                let mainNavVC = self?.storyboard?._mainNavigation
            else {
                print("FAILED TO PARSE!!!")
                return
            }

            self?.present(mainNavVC, animated: true, completion: nil)
        }
    }
}

extension LaunchViewController: FUIAuthDelegate {

    func authUI(_ authUI: FUIAuth, didSignInWith user: User?, error: Error?) {
        if user == nil {
            tryToAuthenticate()
        }
    }
}
