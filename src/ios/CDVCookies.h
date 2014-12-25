//
//  CDVCookies.h
//  Dreamflat
//
//  Created by Dr. E on 25/11/13.
//
//

#import <Cordova/CDVPlugin.h>

@interface CDVCookies : CDVPlugin

- (void)clear:(CDVInvokedUrlCommand*)command;
- (void)setCookie:(CDVInvokedUrlCommand*)command;

@end
